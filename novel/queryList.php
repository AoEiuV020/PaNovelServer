<?php
/**
 * Created by AoEiuV020 on 2018.06.26-17:29:53.
 */

require_once __DIR__ . '/../request.php';
require_once __DIR__ . '/../model/Novel.php';
require_once __DIR__ . '/../query.php';
require_once __DIR__ . '/../connect.php';

try {
    $novelMap = $data;
    logd('query novel list: ' . json_encode($novelMap, JSON_UNESCAPED_UNICODE));
    $resultMap = array();
    foreach ($novelMap as $clientId => $novelArray) {
        $novel = new Novel($novelArray);
        $exists = queryNovel($con, $novel);
        if (is_null($exists)) {
            // 不存在的小说直接无视，
            continue;
        }
        $resultMap[$clientId] = array(
            'chaptersCount' => $exists->chaptersCount,
            'checkUpdateTime' => $exists->checkUpdateTime->format(DATE_ISO8601)
        );
    }
    success($resultMap);
} catch (Throwable $e) {
    catchException($e);
}