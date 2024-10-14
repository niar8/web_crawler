# Web Crawler

## Stack

* [zio](https://github.com/zio/zio)
* [zio-http](https://github.com/zio/zio-http)
* [zio-json](https://github.com/zio/zio-json)
* [jsoup](https://github.com/jhy/jsoup)

## Description

A simple web crawler with one POST endpoint
which can fetch the titles for the given URLs.

It was tested using the following request (via [HTTPie](https://httpie.io/)):

```shell
http post localhost:8080/fetch_titles urls:='["https://www.google.com", "https://wikipedia.org", "https://github.com/", "https://stackoverflow.com/questions"]'
```

The response:

```json
{
  "info": [
    {
      "title": "Google",
      "url": "https://www.google.com"
    },
    {
      "title": "Wikipedia",
      "url": "https://wikipedia.org"
    },
    {
      "title": "GitHub: Let’s build from here · GitHub",
      "url": "https://github.com/"
    },
    {
      "title": "Newest Questions - Stack Overflow",
      "url": "https://stackoverflow.com/questions"
    }
  ]
}
```