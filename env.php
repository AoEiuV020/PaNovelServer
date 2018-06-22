<?php
/**
 * Created by AoEiuV020 on 2018.06.22-09:27:03.
 */

require_once __DIR__ . '/model/MobResponse.php';
require_once __DIR__ . '/DataSource.php';
$ds = new DataSource;

function illegalRequest($message = null)
{
    error_log($message);
    $response = new MobResponse();
    $response->illegalRequest($message);
    die(json_encode($response));
}

function serverError($message = null)
{
    error_log($message);
    $response = new MobResponse();
    $response->serverError($message);
    die(json_encode($response));
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
    /** @noinspection PhpUnusedParameterInspection */
    $errfile,
    /** @noinspection PhpUnusedParameterInspection */
    $errline)
{
    serverError($errstr);
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

function now()
{
    return date('Y-m-d H:i:s');
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


if (file_exists(__DIR__ . '/config.ini.php')) {
    /** @noinspection PhpIncludeInspection */
    include __DIR__ . '/config.ini.php';
} else {
    include __DIR__ . '/config.sample.ini.php';
}
