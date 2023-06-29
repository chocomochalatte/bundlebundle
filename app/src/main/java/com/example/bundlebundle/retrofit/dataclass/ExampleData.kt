package com.example.bundlebundle.retrofit.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//@SerializedName: JSON으로 serialize 될 때 매칭되는 이름을 명시하는 목적으로 사용한다.
//@Expose : object 중 해당 값이 null일 경우, json으로 만들 필드를 자동 생략
data class Baemin(val data: Data)

data class Data(val content: ArrayList<Content>)

data class Content(
    @SerializedName("title")
    val title: String,
    @SerializedName("created")
    val created: String
)