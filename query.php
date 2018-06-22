<?php
/**
 * Created by AoEiuV020 on 2018.06.22-17:01:55.
 */

require_once __DIR__ . '/env.php';

function queryNovel(mysqli $con, Novel $novel)
{
    static $stmt;
    if ($stmt == null) {
        // stmt只需要初始化一次，
        $stmt = $con->prepare('select * from novel where site = ? and author = ? and name = ? limit 1');
    }
    requireArg(!is_null($novel->site), "require site");
    requireArg(!is_null($novel->author), "require author");
    requireArg(!is_null($novel->name), "require name");
    $stmt->bind_param('sss', $novel->site, $novel->author, $novel->name);
    $stmt->execute();
    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        $arr = $result->fetch_assoc();
        $resultNovel = new Novel($arr);
        // 查到小说把id设置到传入的小说对象，和返回和对象和id都可用，
        $novel->id = $resultNovel->id;
        return $resultNovel;
    } else {
        return null;
    }
}