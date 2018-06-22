<?php
/**
 * Created by AoEiuV020 on 2018.06.22-09:27:03.
 */

require_once __DIR__ . '/model/MobResponse.php';
require_once __DIR__ . '/Datasource.php';
$ds = new DataSource;

function apiError(Throwable $t)
{
    $response = new MobResponse();
    $response->fail($t->getMessage());
    die(json_encode($response));
}

function apiErrorString(string $message)
{
    $response = new MobResponse();
    $response->fail($message);
    die(json_encode($response));
}

function success($data)
{
    $response = new MobResponse();
    $response->success($data);
    print json_encode($response);
}

/** @noinspection PhpIncludeInspection */
if (!@include __DIR__ . '/config.ini.php') {
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