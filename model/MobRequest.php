<?php
/**
 * Created by AoEiuV020 on 2018.06.22-10:10:05.
 */

class MobRequest
{
    public $data = "{}";

    private function __construct($data)
    {
        $this->data = $data;
    }

    public static function fromPost()
    {
        $json = json_decode(file_get_contents('php://input'), true);
        return new self($json['data']);
    }
}