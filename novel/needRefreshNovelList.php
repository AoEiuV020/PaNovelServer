<?php
/**
 * Created by AoEiuV020 on 2018.06.22-09:58:06.
 */

require_once __DIR__ . '/../request.php';
require_once __DIR__ . '/../model/Novel.php';
require_once __DIR__ . '/../connect.php';
try {
    $limit = $data;
    logd('query refresh list limit: ' . $limit);
    requireArg(is_int($limit), "limit must be int,");
    requireArg($limit > 0, "require limit > 0,");
    requireArg($limit < 500, "require limit < 500");

    $stmt = $con->prepare("select * from novel where check_update_time > ? order by check_update_time asc limit ?");

    // 在文件保存上一轮最新一本小说的时间，
    // 本轮从这个时间开始，
    // 文件保存的并发不行，先不管了，
    $lastTimeFile = __DIR__ . '/lastTime';

    if (file_exists($lastTimeFile)) {
        $lastTime = file_get_contents($lastTimeFile);
    } else {
        $lastTime = 0;
    }

    $stmt->bind_param('si', $lastTime, $limit);
    $stmt->execute();
    $novelList = [];
    $arr = resultToArray($stmt->get_result());
    foreach ($arr as $item) {
        $novelList[] = new Novel($item);
    }

    if (count($novelList) > 0) {
        $last = $novelList[count($novelList) - 1];
        file_put_contents($lastTimeFile, $last->checkUpdateTime);
    } else {
        file_put_contents($lastTimeFile, 0);
        // 遍历完了后额外尝试一次，只一次，
        $lastTime = 0;
        $stmt->bind_param('si', $lastTime, $limit);
        $stmt->execute();
        $novelList = [];
        $arr = resultToArray($stmt->get_result());
        foreach ($arr as $item) {
            $novelList[] = new Novel($item);
        }
        if (count($novelList) > 0) {
            $last = $novelList[count($novelList) - 1];
            file_put_contents($lastTimeFile, $last->checkUpdateTime);
        }
    }
    $con->close();

    logd("query refresh list later than $lastTime count: " . count($arr));

    success($novelList);
} catch (Throwable $e) {
    catchException($e);
}
