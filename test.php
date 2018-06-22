<?php
$post = file_get_contents('php://input');
print $post;
$json = json_decode($post, true);
print_r($json);