<?php
/**
 * Created by AoEiuV020 on 2018.06.22-09:27:03.
 */

require_once __DIR__ . '/model/MobResponse.php';
require_once __DIR__ . '/Datasource.php';
$ds = new DataSource;

function illegalRequest($message = null)
{
    $response = new MobResponse();
    $response->illegalRequest($message);
    die(json_encode($response));
}

function serverError($message = null)
{
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
function ShutdownHandler()
{
    if (@is_array($error = @error_get_last())) {
        serverError($error['message']);
    };

    return (TRUE);
}

// 成功结束也会调用，
//register_shutdown_function('ShutdownHandler');

if (file_exists(__DIR__ . '/config.ini.php')) {
    /** @noinspection PhpIncludeInspection */
    include __DIR__ . '/config.ini.php';
} else {
    include __DIR__ . '/config.sample.ini.php';
}

function resultToArray(mysqli_result $result)
{
    $arr = [];
    while ($row = $result->fetch_assoc()) {
        $arr[] = $row;
    }
    $result->free();
    return $arr;
}

function requireArg(bool $value, string $message)
{
    if (!$value) {
        illegalRequest($message);
    }
}