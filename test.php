<?php
require_once __DIR__ . '/env.php';
require_once __DIR__ . '/model/Novel.php';

error_reporting(-1);

$novel = new Novel();
$arr = (array)$novel;

// php并发是多进程，不共享变量，
static $count = 0;
++$count;
print($count);

require_once __DIR__ . '/connect.php';

$arr = resultToArray($con->query("select * from novel limit 1"));

$lastTimeFile = __DIR__ . '/novel/lastTime';
print (file_get_contents($lastTimeFile));

unlink($lastTimeFile);
