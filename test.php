<?php
require_once __DIR__ . '/env.php';
require_once __DIR__ . '/query.php';
require_once __DIR__ . '/model/Novel.php';

ini_set('display_errors', 'on');
error_reporting(-1);

include __DIR__ . '/connect.php';

$novel = new Novel();
$novel->site = "起点中文";
$novel->author = "圣骑士的传说";
$novel->name = "修真聊天群";
print_r(queryNovel($con, $novel));
