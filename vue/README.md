# vue

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).


### 20220702-更換大頭貼時，立即顯示更換的圖
[子元件(個人訊息的框)傳遞給父元件(整個框)，父元件再傳遞給子元件(右上角的顯示小框)]
1. Mange.vue初始化頁面時就先從後端取user資料
2. 在person.vue中的save按鈕按下後
3. 觸發父級(Manage.vue)的更新user方法
4. 已有自定義的取得使用者資訊的方法，觸發後賦值給sysUser
5. 再將sysUser傳遞給Header元件，傳給props中的變數