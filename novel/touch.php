<?php
/**
 * Created by AoEiuV020 on 2018.06.22-15:57:10.
 */

require_once __DIR__ . '/../request.php';
require_once __DIR__ . '/../model/Novel.php';
require_once __DIR__ . '/../query.php';
require_once __DIR__ . '/../update.php';

try {
    $novel = new Novel($data);
    error_log('touch novel: ' . json_encode($novel));
    requireArg(!is_null($novel->site), "require site");
    requireArg(!is_null($novel->author), "require author");
    requireArg(!is_null($novel->name), "require name");
    requireArg(!is_null($novel->chaptersCount), "require chapters_count");
    include __DIR__ . '/../connect.php';
    $exists = queryNovel($con, $novel);
    $hasUpdate = false;
    if ($exists == null) {
        $hasUpdate = false;
    } else {
        if ($novel->chaptersCount < $exists->chaptersCount) {
            // 如果没有更新，
            $hasUpdate = false;
        } else {
            // 如果有更新，
            // TODO: 这里要推到极光，
            $hasUpdate = true;
        }
        // 统一更新成服务器收到的时间，不保存客户端上传的时间，可能受时区影响，
        $novel->checkUpdateTime = now();
        $novel->receiveUpdateTime = max($novel->receiveUpdateTime, $exists->receiveUpdateTime);
        // 按理说刷出来的章节数不会小于服务器上的，但是以防万一，以新上传的为准，覆盖旧的，
        updateNovel($con, $novel);
    }

    $con->close();
    success($hasUpdate);
} catch (Throwable $e) {
    serverError($e->getMessage());
}
