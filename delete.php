<?php
/**
 * Created by AoEiuV020 on 2018.06.22-21:48:34.
 */

require_once __DIR__ . '/env.php';

function deleteNovel(mysqli $con, Novel $novel)
{
    error_log('delete: ' . json_encode($novel));
    assertArg($novel->id, 'delete require id,');
    static $stmt;
    if ($stmt == null) {
        // stmt只需要初始化一次，
        $stmt = $con->prepare('delete from novel where id = ?');
    }
    $stmt->bind_param('i', $novel->id);
    $stmt->execute();
    assertArg(!$stmt->error, 'delete error: ' . $stmt->error);
}
