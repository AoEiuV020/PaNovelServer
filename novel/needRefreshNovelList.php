<?php
/**
 * Created by AoEiuV020 on 2018.06.22-09:58:06.
 */

require_once __DIR__ . '/../request.php';
require_once __DIR__ . '/../model/Novel.php';
try {
    $limit = $data;
    error_log('query refresh list limit: ' . $limit);
    requireArg(is_int($limit), "limit must be int,");
    requireArg($limit > 0, "require limit > 0,");
    requireArg($limit < 500, "require limit < 500");

    include __DIR__ . '/../connect.php';
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
    $arr = resultToArray($stmt->get_result());

    if (count($arr) > 0) {
        $last = new Novel($arr[count($arr) - 1]);
        file_put_contents($lastTimeFile, $last->check_update_time);
    } else {
        file_put_contents($lastTimeFile, 0);
        // 遍历完了后额外尝试一次，只一次，
        $lastTime = 0;
        $stmt->bind_param('si', $lastTime, $limit);
        $stmt->execute();
        $arr = resultToArray($stmt->get_result());
        if (count($arr) > 0) {
            $last = new Novel($arr[count($arr) - 1]);
            file_put_contents($lastTimeFile, $last->check_update_time);
        }
    }
    $con->close();

    error_log("query refresh list later than $lastTime count: " . count($arr));

    success($arr);
} catch (Throwable $e) {
    serverError($e->getMessage());
}
