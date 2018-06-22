<?php
/**
 * Created by AoEiuV020 on 2018.06.22-21:28:19.
 */

require_once __DIR__ . '/env.php';

function insertNovel(mysqli $con, Novel $novel)
{
    error_log('insert: ' . json_encode($novel));
    static $stmt;
    if ($stmt == null) {
        // stmt只需要初始化一次，好像没多大意义，只调用一次，
        $stmt = $con->prepare('insert into novel(site, author, name, detail, chapters_count, receive_update_time, check_update_time) values (?, ?, ?, ?, ?, ?, ?)');
    }
    $stmt->bind_param('ssssiss', $novel->site, $novel->author, $novel->name,
        $novel->detail, $novel->chaptersCount, $novel->receiveUpdateTime, $novel->checkUpdateTime);
    $stmt->execute();
    assertArg(!$stmt->error, 'insert error: ' . $stmt->error);
    $novel->id = $stmt->insert_id;
}
