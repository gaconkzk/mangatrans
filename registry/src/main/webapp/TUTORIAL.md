# VueJS, Webpack and Material Design Lite

## Install vue-cli
    yarn global add vue-cli

Vue-cli comes along with a few templates. We will choose 
[pwa template](https://github.com/vuejs-templates/pwa). 
Vue-cli is going to create a dummy VueJS application 
with Webpack, vue-loader (hot reload!), a proper manifest 
file and basic offline support through service workers.

Vue pwa template is built on top of Vue webpack template. 
[Webpack](https://webpack.github.io/) is a modern and 
powerful module bundler for Javascript application that 
will process and build our assets.

    $cd /path/to/my-project 
    $vue init pwa .

You will be asked a few questions. Here is the configuration I used:

    ? Project name theflies-registry
    ? Project description The Flies Registry Service
    ? Author gaconkzk
    ? Vue build standalone
    ? Install vue-router? Yes
    ? Use ESLint to lint your code? Yes
    ? Pick an ESLint preset Standard
    ? Setup unit tests with Karma + Mocha? Yes
    ? Setup e2e tests with Nightwatch? Yes
    
    vue-cli · Generated "theflies-registry".    
     
This process creates a project folder with following subfolders:
- build: contains webpack and vue-loader configuration files
- config: contains our app config (environments, parameters…)
- src: source code of our application
- static: images, css and other public assets
- test: unit test files propelled by Karma & Mocha

Then run:

    yarn install
    yarn start

This will open your browser on `localhost:8080`:

<img style="float: right;" src="https://cdn-images-1.medium.com/max/1000/1*WQFOAsM5rqgzfEsYVb3WeA.png">

## Manifest.json: make your application installable
One of the biggest plus of PWA is that applications are easily installable and sharable. All web applications that provides a valid 
`manifest.json` file in their `index.html` are installable.

Vue pwa template provides a default `manifest.json` file.

Edit default `static/manifest.json` file to customize your project:

```json
{
  "name": "Cropchat",
  "short_name": "Cropchat",
  "icons": [
    {
      "src": "/static/img/icons/android-chrome-192x192.png",
      "sizes": "192x192",
      "type": "image/png"
    },
    {
      "src": "/static/img/icons/android-chrome-512x512.png",
      "sizes": "512x512",
      "type": "image/png"
    }
  ],
  "start_url": "/",
  "display": "fullscreen",
  "orientation": "portrait",
  "background_color": "#2196f3",
  "theme_color": "#2196f3"
}
```
Take a look at `index.html` and see that manifest is already linked here:

```html 
<link rel="manifest" href="/static/manifest.json"> 
```
Make sure you also have a viewport declared in the head section of `index.html`:

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```

That’s it! Let’s try to install TheFlies on a mobile device. There are many ways to access our 
`localhost:8080` from distant mobile device. My favorite one is to use [`ngrok`](https://ngrok.com/).

Ngrok is a service that relays your local environment on a distant dns, for free!

To install it:

    yarn global add ngrok

> Note (Windows): We might need to edit the script in `ngrok` installed location.  

Then, run:

    ngrok http 8080

That should give you the follolwing output:

    ngrok by @inconshreveable                                                                   (Ctrl+C to quit)
                                                                                                            
    Session Status                online                                                                        
    Version                       2.1.18                                                                        
    Region                        United States (us)                                                            
    Web Interface                 http://127.0.0.1:4040                                                         
    Forwarding                    http://5ef29506.ngrok.io -> localhost:8080                                    
    Forwarding                    https://5ef29506.ngrok.io -> localhost:8080                                   
                                                                                                                
    Connections                   ttl     opn     rt1     rt5     p50     p90                                   
                                  39      3       0.01    0.01    120.01  881.89

Browse `ngrok` url `http://5ef29506.ngrok.io` with your smartphone. You can add it to your 
device desktop!

![i1](https://cdn-images-1.medium.com/max/600/1*uJDoOzhx9YUiggFEy6QgmQ.png) 
![i2](https://cdn-images-1.medium.com/max/600/1*IYpNDhvG63WeN12lMXsMyg.png)
![i3](https://cdn-images-1.medium.com/max/600/1*shHpjQAHdT3ILEwlKttULg.png)
![i4](https://cdn-images-1.medium.com/max/600/1*XdZJPMwFU6gRT3Rvf-aqvA.png)

To learn more about ngrok, you can read Matthieu Auger’s article: 
[Expose your local environment to the world with ngrok](http://www.theodo.fr/blog/2016/06/expose-your-local-environment-to-the-world-with-ngrok/)

