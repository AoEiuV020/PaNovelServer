<?php
/**
 * Created by AoEiuV020 on 2018.06.22-09:27:03.
 */

require_once __DIR__ . '/vendor/autoload.php';
require_once __DIR__ . '/model/MobResponse.php';
require_once __DIR__ . '/DataSource.php';
require_once __DIR__ . '/JpushConfig.php';
$ds = new DataSource();
$jc = new JpushConfig();

function logd($message)
{
    // 暂且不打日志，
}

function illegalRequest($message = null)
{
    logd($message);
    $response = new MobResponse();
    $response->illegalRequest($message);
    die(json_encode($response));
}

function serverError($message = null)
{
    logd($message);
    $response = new MobResponse();
    $response->serverError($message);
    die(json_encode($response));
}

function catchException(Throwable $e)
{
    $errstr = $e->getMessage();
    $errfile = basename($e->getFile());
    $errline = $e->getLine();
    serverError("$errstr at <$errfile,$errline>");
}

function success($data)
{
    $response = new MobResponse();
    $response->success($data);
    die(json_encode($response));
}

ini_set('display_errors', 'off');
ini_set('html_errors', 0);
error_reporting(0);
function myErrorHandler(
    /** @noinspection PhpUnusedParameterInspection */
    $errno,
    $errstr,
    $errfile,
    $errline)
{
    $errfile = basename($errfile);
    serverError("$errstr at <$errfile,$errline>");
}

set_error_handler("myErrorHandler");

function resultToArray(mysqli_result $result)
{
    $arr = [];
    while ($row = $result->fetch_assoc()) {
        $arr[] = $row;
    }
    $result->free();
    return $arr;
}

// 接口要求请求参数满足什么条件，
function requireArg($value, string $message)
{
    if (!$value) {
        illegalRequest($message);
    }
    return $value;
}

// 服务器断言什么，有错是服务器的错，
// 不带bool， 可以传任意值，
function assertArg($value, string $message)
{
    if (!$value) {
        serverError($message);
    }
    return $value;
}

define('SQL_TIME_FORMAT', 'Y-m-d H:i:s');

function now()
{
    return date(SQL_TIME_FORMAT);
}

function sqlTime($time)
{
    return date(SQL_TIME_FORMAT, $time);
}


function convertUnderline($str)
{
    $str = preg_replace_callback('/([-_]+([a-z]{1}))/i', function ($matches) {
        return strtoupper($matches[2]);
    }, $str);
    return $str;
}

function humpToLine($str)
{
    $str = preg_replace_callback('/([A-Z]{1})/', function ($matches) {
        return '_' . strtolower($matches[0]);
    }, $str);
    return $str;
}

function timeArrayToTime(array $array)
{
    // 这东西不带时区，看起来就很不靠谱，
    // 最后一个is_dst明明声明里有，但是传入会报错，
    //  mktime() expects at most 6 parameters, 7 given
    return mktime($array['hour'], $array['minute'], $array['second'],
        $array['month'], $array['day'], $array['year']/*, $array['is_dst']*/);
}


if (file_exists(__DIR__ . '/config.ini.php')) {
    /** @noinspection PhpIncludeInspection */
    include __DIR__ . '/config.ini.php';
} else {
    include __DIR__ . '/config.sample.ini.php';
}
