<?php
require_once __DIR__ . '/env.php';
require_once __DIR__ . '/query.php';
require_once __DIR__ . '/model/Novel.php';
require_once __DIR__ . '/update.php';
require_once __DIR__ . '/insert.php';

ini_set('display_errors', 'on');
error_reporting(-1);


print_r($jc);
$novel = new Novel(array());
$novel->site = '起点中文';
$novel->author = '圣骑士的传说';
$novel->name = '修真聊天群';
$novel->chaptersCount = 14;
$novel->receiveUpdateTime = new DateTime(now());

print($novel->receiveUpdateTime->format(DATE_ISO8601));
print "\n";

$t = $novel->receiveUpdateTime;
print(strtotime('-3 days'));
print "\n";
print($t->getTimestamp());
print "\n";
