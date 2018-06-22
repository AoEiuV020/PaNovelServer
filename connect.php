<?php
/**
 * Created by AoEiuV020 on 2018.06.22-09:30:53.
 */

require_once __DIR__ . '/env.php';
try {
    $con = new mysqli($ds->host, $ds->username, $ds->password, $ds->database);
    assertArg(!$con->connect_error, 'connect error: ' . $con->connect_error);
    $con->set_charset($ds->charset);
} catch (Throwable $e) {
    serverError($e->getMessage());
}
