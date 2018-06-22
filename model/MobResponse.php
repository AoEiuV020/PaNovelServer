<?php
/**
 * Created by AoEiuV020 on 2018.06.22-08:30:05.
 */

class MobResponse
{
    public $code = -1;
    public $data = "{}";

    public function serverError($message)
    {
        $this->code = 500;
        $this->data = json_encode($message);
    }

    public function illegalRequest($message)
    {
        $this->code = 400;
        $this->data = json_encode($message);
    }

    public function success($data)
    {
        $this->code = 200;
        $this->data = json_encode($data);
    }
}