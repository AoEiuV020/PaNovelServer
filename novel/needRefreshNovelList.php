<?php
/**
 * Created by AoEiuV020 on 2018.06.22-09:58:06.
 */

require_once __DIR__ . '/../request.php';
try {
    include __DIR__ . '/../connect.php';
    $stmt = $con->prepare("select * from novel order by check_update_time limit ?");
    $limit = json_decode($data);
    $stmt->bind_param('i', $limit);
    $stmt->execute();
    $arr = resultToArray($stmt->get_result());
    $con->close();
    success($arr);
} catch (Throwable $e) {
    apiError($e);
}
