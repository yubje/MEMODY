# Sub PJT 2



### Contents

- [Commit | Merge](#Commit  | Merge) 
- [역할분담](#역할 분담)
- [Branch 전략](# GitLab Branch 전략)
- [지라](# 지라)
- [컨벤션](# 컨벤션)
- [와이어프레임](# 와이어프레임)

### Commit  | Merge | Pull

- 시간

  - AM 10:00  | PM 16:00

  - 하루에 한번은 `develop`에 MR
  - `Front` / `Back` 에 각자 `Feature` MR 후  `develop`  Pull 땡겨서 최신화 

- Commit Message Rule

  `{JIRA issue ID} | {진행상황} | {기능 명}`

  ex) `S03P11A305-1 | Done | 회원가입`

  `[마무리] | {날짜 }| README.md`

  - ex) `[마무리] | 2020.07.21 | README.md`

  

  **진행상황** 

  - Todo
  - In Progress
  - Done

  

### 역할 분담

- Front 
  - **유빈**
  - 재영
  - 유진
- Back
  - **형택**
  - 민경



### GitLab Branch 전략

- Master

  - Develop

    - Front

      - Feature/에픽
        - Feature/에픽/스토리(?)
      - Feature/에픽

    - Back

      - Feature/에픽

      

### 지라

- Epic : Front / 회원관리 (User)
  - Stroy : 로그인 (Login)



### 컨벤션

- [JavaScript](https://github.com/airbnb/javascript)

  > 자세한 내용은 공식문서 참고

  ``` javascript
  exports default {
    created() {
      }
    },
    methods: {
      setInputValue(state) {
  	  console.log('example')
      },	
    },
  }
  ```

  **Naming Convention**

  > 기본적으로 Full Name으로 씁시당

  ```javascript
  ex) res => response, err => error (keyword 걸리는건 제외입니다.)
  
  ```

  

  **Whitespace** [띄어쓰기 보세요~~]

  - Use soft tabs set to 2 spaces.

    ```javascript
    // bad
    function () {
    ∙∙∙∙var name;
    }
    
    // bad
    function () {
    ∙var name;
    }
    
    // good
    function () {
    ∙∙var name;
    }
    ```

  - Place 1 space before the leading brace.

    ```javascript
    // bad
    function test(){
      console.log('test');
    }
    
    // good
    function test() {
      console.log('test');
    }
    
    // bad
    dog.set('attr',{
      age: '1 year',
      breed: 'Bernese Mountain Dog'
    });
    
    // good
    dog.set('attr', {
      age: '1 year',
      breed: 'Bernese Mountain Dog'
    });
    ```

  - Place 1 space before the opening parenthesis in control statements (`if`, `while` etc.). Place no space before the argument list in function calls and declarations.

    ```javascript
    // bad
    if(isJedi) {
      fight ();
    }
    
    // good  띄어쓰기 보세요 ~~
    if (isJedi) {
      fight();
    }
    ```

  - Set off operators with spaces.

    ```javascript
    // bad
    var x=y+5;
    
    // good
    var x = y + 5;
    ```

  - Leave a blank line after blocks and before the next statement

    ```javascript
    // bad
    if (foo) {
      return bar;
    }
    return baz;
    
    // good
    if (foo) {
      return bar;
    }
    
    return baz;
    
    // bad
    var obj = {
      foo: function () {
      },
      bar: function () {
      }
    };
    return obj;
    
    // good
    var obj = {
      foo: function () {
      },
    
      bar: function () {
      }
    };
    
    return obj;
    ```

  **Commas**

  - Leading commas: **Nope.**

    ```javascript
    // bad
    var story = [
        once
      , upon
      , aTime
    ];
    
    // good
    var story = [
      once,
      upon,
      aTime
    ];
    ```

  **Naming Convention**

  - Use PascalCase when naming constructors or classes.

    ```javascript
    // bad
    function user(options) {
      this.name = options.name;
    }
    
    var bad = new user({
      name: 'nope'
    });
    
    // good
    function User(options) {
      this.name = options.name;
    }
    
    var good = new User({
      name: 'yup'
    });
    ```

    

  ```javascript
  // Keywords : 
  break,do,instanceof,typeof,case,else,new,var,catch,finally,return,void,continue,for,switch,while,
  debugger,function,this,with,default,if,throw,delete,in,try
  
  // Key값을 키워드로 잡지 말기
  // good
  var superman = {
    defaults: { clark: 'kent' },
    hidden: true
  };
  ```

  **Strings**

  ```javascript
  // String 쓸 때 '' 쓰기 	
  // bad
  var name = "Bob Parr";
  
  // good
  var name = 'Bob Parr';
  ```

  **Properties**

  ```javascript
  var luke = {
    jedi: true,
    age: 28
  };
  
  // bad
  var isJedi = luke['jedi'];
  
  // good
  var isJedi = luke.jedi;
  
  //Use subscript notation [] when accessing properties with a variable.
  var luke = {
    jedi: true,
    age: 28
  };
  
  function getProp(prop) {
    return luke[prop];
  }
  
  var isJedi = getProp('jedi');
  ```

  

- Use `===` and `!==` over `==` and `!=`.

  - **Objects** evaluate to **true**

  - **Undefined** evaluates to **false**

  - **Null** evaluates to **false**

  - **Booleans** evaluate to **the value of the boolean**

  - **Numbers** evaluate to **false** if **+0, -0, or NaN**, otherwise **true**

  - **Strings** evaluate to **false** if an empty string `''`, otherwise **true**

  - use shortcuts

  - ```javascript
    // bad
    if (name !== '') {
      // ...stuff...
    }
    
    // good
    if (name) {
      // ...stuff...
    }
    
    // bad
    if (collection.length > 0) {
      // ...stuff...
    }
    
    // good
    if (collection.length) {
      // ...stuff...
    }
    ```

  **if elif else**

  ```javascript
  // bad
  if (test) {
    thing1();
    thing2();
  }
  else {
    thing3();
  }
  
  // good
  if (test) {
    thing1();
    thing2();
  } else 
    thing3();
  }
  ```

- [Java](https://myeonguni.tistory.com/m/1596)



### 와이어프레임

- 발사믹

