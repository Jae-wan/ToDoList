### ✍ 서적 참고 프로젝트 (Joyce의 안드로이드 앱 프로그래밍 with 코틀린)
<br>

> <b> 프로젝트 명 : 할 일 리스트 (To Do List) <br> </b>
> 프로젝트 설명 : 나만의 할 일 리스트를 만들어보기. (Room을 이용하여)

> <b> 개발 환경 : Android Studio </b>
> * minSdk : 26
> targetSdk : 31

> * <b> 핵심 구성요소 </b>  <br>
> Recycler View : View를 재활용(Recycle)하여 리스트뷰보다 더 효율적으로 리스트를 보여주는 뷰를 활용 <br>
> Room 지속성 라이브러리 : Local DataBase 구축을 도와주는 라이브러리 <br>
> Alert Dialog : 사용자의 의사를 물어보는 대화 상자 (팝업) <br>
> viewBinding : 뷰를 더 편리하게 사용할 수 있는 라이브러리 (기존의 코틀린 익스텐션 대체) <br>

> * Room 지속성 라이브러리 사용을 위한 'kotlin-kapt' 플러그인 추가 <br>
> => <b> [KAPT : Kotlin Annotation Processing Tool에 대해서](https://timradder.tistory.com) </b>



> * <b> 코어기능 </b>
>     * 할 일 리스트 보기 (To Do List)
>     * 할 일 추가 (Add)
>     * 할 일 삭제 (Remove)
>     * 할 일 중요도 표시 (Importance)

> * <b> 부가기능 </b>
>     * 할일 추가와 함께 중요도를 선택할 수 있도록 기능 추가
>     * 꾹 누른 상태에서 동작 메뉴 보여주기 (할 일 삭제(Remove) 기능 나타내기)

> * <b> 디자인 </b>
>     * 추가중..


<br>

---

<br>

* 레이아웃 구성 예상도
<table>
  <tr>
    <td><img alt="" src="https://user-images.githubusercontent.com/57258381/161485314-6f3f7673-75df-4662-a231-f8feb80c630a.png" height="640" width="360"> </td>
    <td><img alt="" src="https://user-images.githubusercontent.com/57258381/161485364-6c60ed1c-2bcf-4c43-b630-af456a85057b.png" height="640" width="360"> </td>
  </tr>
  </table>
  
---


<h2> 사전 작업 - 1 </h2>

* viewBinding Config

```kotlin

android{
  ... 생략 ...
  defaultConfig {
  ... 생략 ...
  }
  buildFeatures {
    viewBinding true
  }
}
  
```


<h2> 사전 작업 - 2 </h2>

* Room Library Config <br>
아래와 같이 추가

```kotlin
// module 수준의 build.gradle
plugins {
  ... 생략 ...
    id 'kotlin-kapt' 
}

    ... 생략 ...

// module 수준의 build.gradle
dependencies {
  ... 생략 ...
    def room_version ="2.3.0"
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
  ... 생략 ...
}
```
















