<?php
require_once __DIR__ . '/env.php';
$post = '
{
"data": {
    "id": 12
}
}
';
print $post;
$json = json_decode($post, true);
print_r($json);
$data = $json['data'];
print_r($data);
$f = is_string($data);
print $f;
var_dump($f);

$v = false;
print '' . $f;

print gettype($data);

$a = $json['dat'] ?? null;
if ($a != null) {
    throw new Exception("hhh");
}

