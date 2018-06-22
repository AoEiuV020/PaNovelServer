<?php
/**
 * Created by AoEiuV020 on 2018.06.21-22:52:50.
 */

require_once __DIR__ . '/connect.php';
$stmt = $con->prepare("select * from novel where name = ? and author = ? and site = ?");
$name = "修真聊天群";
$author = "圣骑士的传说";
$site = "31小说";
$id = 9;
$stmt->bind_param("sss", $name, $author, $site);
$stmt->execute();
$result = $stmt->get_result();
$con->close();
$novels = [];
while ($row = $result->fetch_assoc()) {
    $novels[] = $row;
}
$response = new MobResponse();
$response->success($novels);
print json_encode($response);
