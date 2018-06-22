<?php
require_once __DIR__ . '/env.php';
require_once __DIR__ . '/query.php';
require_once __DIR__ . '/model/Novel.php';
require_once __DIR__ . '/update.php';

ini_set('display_errors', 'on');
error_reporting(-1);

include __DIR__ . '/connect.php';

$novel = new Novel();
$novel->id = 14;
$novel->site = "起点中文";
$novel->author = "圣骑士的传说";
$novel->name = "修真聊天群";
$novel->detail = "3602691";
$novel->chaptersCount = 12;
$novel->receiveUpdateTime = now();
$novel->checkUpdateTime = now();
updateNovel($con, $novel);
