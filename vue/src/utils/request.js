import axios from 'axios'

const request = axios.create({
    baseURL: '/api',  // 此為統一加上前綴[/api]，也就是說所有的api都會有[/api]前綴，在頁面中寫api的時候就不要加[/api]了
    // ，否則會出現兩次[/api]，例如:[/api/api/user]這樣就會出錯了！！！
    timeout: 5000
})

// request 攔截器
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    // config.headers['token'] = user.token;  // setting header
    return config
}, error => {
    return Promise.reject(error)
});

// response 攔截器
request.interceptors.response.use(
    response => {
        let res = response.data;
        if (response.config.responseType === 'blob') {
            return res
        }
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)

export default request