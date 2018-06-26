<?php
require_once __DIR__ . '/env.php';
require_once __DIR__ . '/query.php';
require_once __DIR__ . '/model/Novel.php';
require_once __DIR__ . '/update.php';
require_once __DIR__ . '/insert.php';

ini_set('display_errors', 'on');
error_reporting(-1);

include __DIR__ . '/connect.php';

// 最后的时区java格式的有冒号:，php可以解析，
$t = date_parse('2018-06-22T22:02:33.533+08:00');
print_r($t);
print_r(timeArrayToTime($t));

// DateTime比较靠谱，
$d = new DateTime('2018-06-22T22:02:33.533+08:00');
print_r($d);

$novel = new Novel();
$novel->id = 14;
$novel->site = "起点中文";
$novel->author = "圣骑士的传说";
$novel->name = "修真聊天群";
$novel->detail = "3602691";
$novel->chaptersCount = 13;
$novel->receiveUpdateTime = $d->format(SQL_TIME_FORMAT);
$novel->checkUpdateTime = $d->format(SQL_TIME_FORMAT);
insertNovel($con, $novel);
