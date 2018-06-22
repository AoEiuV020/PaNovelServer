<?php
/**
 * Created by AoEiuV020 on 2018.06.22-15:57:10.
 */

require_once __DIR__ . '/../request.php';
require_once __DIR__ . '/../model/Novel.php';
try {
    $novel = new Novel($data);
    include __DIR__ . '/../connect.php';

    $con->close();
} catch (Throwable $e) {
    serverError($e->getMessage());
}
