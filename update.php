<?php
/**
 * Created by AoEiuV020 on 2018.06.22-18:41:12.
 */

require_once __DIR__ . '/env.php';

function updateNovel(mysqli $con, Novel $novel)
{
    error_log('update: ' . json_encode($novel));
    assertArg($novel->id, 'update require id,');
    static $stmt;
    if ($stmt == null) {
        // stmt只需要初始化一次，
        $stmt = $con->prepare('update novel set site = ?, author = ?, name = ?, detail = ?, chapters_count = ?, receive_update_time = ?, check_update_time = ? where id = ?');
    }
    $stmt->bind_param('ssssissi', $novel->site, $novel->author, $novel->name,
        $novel->detail, $novel->chaptersCount, $novel->receiveUpdateTime, $novel->checkUpdateTime,
        $novel->id);
    $stmt->execute();
    assertArg(!$stmt->error, 'update error: ' . $stmt->error);
}
