<?php
/**
 * Created by AoEiuV020 on 2018.06.22-10:09:53.
 */

require_once __DIR__ . '/model/MobRequest.php';
require_once __DIR__ . '/env.php';
try {
    $data = MobRequest::fromPost()->data;
} catch (Throwable $e) {
    apiError($e);
}
