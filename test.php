<?php
require_once __DIR__ . '/env.php';
require_once __DIR__ . '/query.php';
require_once __DIR__ . '/model/Novel.php';
require_once __DIR__ . '/update.php';
require_once __DIR__ . '/insert.php';
require_once __DIR__ . '/push.php';

ini_set('display_errors', 'on');
error_reporting(-1);


$novel = new Novel(array());
$novel->site = '起点中文';
$novel->author = '圣骑士的传说';
$novel->name = '修真聊天群';
$novel->chaptersCount = 14;
$novel->receiveUpdateTime = new DateTime(now());
$novel->checkUpdateTime = new DateTime(now());

print_r($jc);
pushUpdate($novel);