<?php
/**
 * Created by AoEiuV020 on 2018.06.22-10:09:53.
 */

require_once __DIR__ . '/env.php';

$data = null;
try {
    $post = file_get_contents('php://input');
    $json = json_decode($post, true);
    $data = $json['data'] ?? null;
    if (is_string($data)) {
        $data = json_decode($data, true);
    } else if (is_null($data)) {
        throw new Exception('require data,');
    }
} catch (Throwable $e) {
    illegalRequest($e->getMessage());
}
