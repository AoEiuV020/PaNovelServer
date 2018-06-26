<?php
/**
 * Created by AoEiuV020 on 2018.06.22-15:57:10.
 */

require_once __DIR__ . '/../request.php';
require_once __DIR__ . '/../model/Novel.php';
require_once __DIR__ . '/../query.php';
require_once __DIR__ . '/../update.php';
require_once __DIR__ . '/../insert.php';
require_once __DIR__ . '/../delete.php';
require_once __DIR__ . '/../push.php';
require_once __DIR__ . '/../connect.php';

try {
    $novel = new Novel($data);
    logd('touch novel: ' . json_encode($novel));
    requireArg(!is_null($novel->site), "require site");
    requireArg(!is_null($novel->author), "require author");
    requireArg(!is_null($novel->name), "require name");
    requireArg(!is_null($novel->chaptersCount), "require chaptersCount");
    $exists = queryNovel($con, $novel);
    $hasUpdate = false;
    if ($exists == null) {
        insertNovel($con, $novel);
        $hasUpdate = false;
    } else {
        if ($novel->chaptersCount <= $exists->chaptersCount) {
            // 如果没有更新，
            $hasUpdate = false;
        } else {
            // 如果有更新，
            $hasUpdate = true;
        }
        logd("hasUpdate $hasUpdate, {$novel->chaptersCount}/{$exists->chaptersCount}");
        if (!$hasUpdate && $novel->receiveUpdateTime < sqlTime(strtotime('-3 day'))) {
            // 如果小说三天没更新，就删除，
            deleteNovel($con, $exists);
        } else {
            // 统一更新成服务器收到的时间，不保存客户端上传的时间，可能受时区影响，
            $novel->checkUpdateTime = new DateTime(now());
            // receiveUpdateTime保留，以客户端上传的为准，
            // 按理说刷出来的章节数不会小于服务器上的，但是以防万一，以新上传的为准，覆盖旧的，
            updateNovel($con, $novel);
        }
        if ($hasUpdate) {
            pushUpdate($novel);
        }
    }

    $con->close();
    success($hasUpdate);
} catch (Throwable $e) {
    catchException($e);
}
