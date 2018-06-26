<?php
/**
 * Created by AoEiuV020 on 2018.06.22-10:07:11.
 */

class Novel
{
    public $id;
    public $site;
    public $author;
    public $name;
    public $detail;
    public $chaptersCount;
    public $receiveUpdateTime;
    /**
     * @var DateTime
     */
    public $checkUpdateTime; // 2018-06-19 18:03:54

    public function __construct(array $array = null)
    {
        $this->id = $array['id'] ?? null;
        $this->site = $array['site'] ?? null;
        $this->author = $array['author'] ?? null;
        $this->name = $array['name'] ?? null;
        $this->detail = $array['detail'] ?? null;
        // 客户端传上来的是驼峰，sql查出来的是下划线，
        $this->chaptersCount = $array['chaptersCount'] ?? $array[humpToLine('chaptersCount')] ?? null;
        $this->receiveUpdateTime = $array['receiveUpdateTime'] ?? $array[humpToLine('receiveUpdateTime')] ?? null;
        $this->checkUpdateTime = $array['checkUpdateTime'] ?? $array[humpToLine('checkUpdateTime')] ?? null;
        if (!is_null($this->receiveUpdateTime)) {
            $this->receiveUpdateTime = (new DateTime($this->receiveUpdateTime))->format(SQL_TIME_FORMAT);
        }
        if (!is_null($this->checkUpdateTime)) {
            $this->checkUpdateTime = (new DateTime($this->checkUpdateTime))->format(SQL_TIME_FORMAT);
        }
    }

    public function bookId()
    {
        return "$this->name.$this->author.$this->site";
    }

}