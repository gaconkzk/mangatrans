# Frontend Seed
This document contains the directives for creation and development. Use the Index below to navigate through this brief documentation.

---

# Summary
1. [tl;dr;](#tldr)
* [How to start this project](#how-to-start-this-project)
* [Text editor](#text-editor)
  * [Which one to use?](#which-one-to-use)
  * [Plugins list](#plugins-list)
  * [Theme and recommended syntax](#theme-and-recommended-syntax)
  * [.editorconfig](#editorconfig)
* [HTML Guide](#html-guide)
  * [Example](#html-guide-example)
* [CSS Guide](#css-guide)
* [JavaScript Guide](#javascript-guide)
* [Node.js and Package Manager](#nodejs-and-package-manager)
  * [Node.js](#nodejs)
  * [Package Manager](#package-manager)
  * [How to install Yarn](#how-install-yarn)
  * [Some of the advantages of using Yarn](#yarn-advantages)
* [Seed](#seed)
  * [Architecture](#architecture)
  * [Vue.js](#vuejs)
  * [Creating component](#creating-component)

---

# <a id="tldr">tl;dr;</a>

```shell

$ npm install -g yarn phantomjs

# Then install the dependencies
$ yarn install

# Then run the application
$ yarn run dev

```

--- 


# <a id="how-to-start-this-project"></a>How to start this project

To start this project, have yarn installed. If you do not have it, read [How to install yarn](#how-install-yarn).

With yarn installed, run the following commands:

```bash
$ yarn install && yarn run dev
```

After the dependency download completes, the application will automatically upload by opening a browser window.

Common Commands:
```bash
# Starts server with hot reload on localhost: 8080
$ yarn run dev

# Generate build for minification production
$ yarn run build

# Runs unit tests
$ yarn run unit

# Wheel tests e2e
$ yarn run e2e

# Runs all tests
$ yarn test

# Adding dependencies to the project
$ yarn add packageName[@versionNumber]

# Remove dependencies in the project
$ yarn remove packageName
```

# <a id="text-editor"></a>Text Editor

## <a id="which-one-to-use"></a>Which one to use?
O The recommended text editor is **[Atom](https://atom.io)**. This software is free and hackable. It has several plugins and is very similar to  [SublimeText](www.sublimetext.com).

## <a id="plugins-list"></a>List of plugins
The plugins needed to enhance the developer experience are ([OP] are optional):
1. **atom-beautify**: color code of the files in development;
* **emmet**: allows you to write HTML faster using the Tab key;
* **linter**: checks for various files that have validation rules;
* **linter-eslint**: makes use of ESLint to validate files that contain JavaScript;
* **autocomplete-modules**: autocomplete for imports;
* **turbo-javascript**: autocomplete for javascript;
* **language-vue**: adds syntax highlighting and snippets;
* **vue-format**: atom-beautify for .vue files;
* [OP]**pigments**: rgb, rgba and hex are left with a color preview;
* [OP]**color-picker**: color picker for atom;
* [OP]**linter-write-good**: autocomplete for writing in English;
* [OP]**file-icons**: places icons next to file names;
* [OP]**markdown-preview-plus**: good for writing *.md* files.

## <a id="theme-and-recommended-syntax"></a> Theme and recommended syntax
* atom-material-ui
* atom-material-[dark|light]

## <a id="editorconfig"></a>.editorconfig
.editorconfig files are files that must be placed at the root of the project. These files are read by the text editor and define how the files will be created, such as whether tab or spaces are used to separate information; An example of this file is:
```
root = true

[*]
charset = utf-8
indent_style = space
indent_size = 2
end_of_line = lf
insert_final_newline = true
trim_trailing_whitespace = true
```

---

# <a id="html-guide"></a>HTML Guide
An example HTML file can be seen below:
<a id="html-guide-example"></a>
```html
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="keywords" content="palavras,chaves,separadas,por,vírgula,sem,espaço">
    <meta name="author" content="best site"/>
    <meta name="description" content="A page's description, usually one or two sentences."/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Social Media Meta Card Tags -->
    <!-- Open Graph -->
    <meta property="og:site_name" content="The best site">
    <meta property="og:url" content="https://bestsite.com">
    <meta property="og:title" content="The best site"/>
    <meta property="og:description" content="description goes here"/>
    <!-- Multiplas imagens com multiplas resoluções/tipos podem ser usadas -->
    <meta property="og:image" content="https://bestsite.com/logo.png"/>
    <meta property="og:image:type" content="image/png">
    <meta property="og:image:width" content="1200">
    <meta property="og:image:height" content="1200">
    <!-- Twitter cards -->
    <meta property="twitter:card" content="summary"/>
    <meta property="twitter:site" content="bestsite"/>
    <meta property="twitter:site:id" content="7812312"/>
    <meta property="twitter:title" content="The best site"/>
    <meta property="twitter:description" content="description goes here"/>
    <meta property="twitter:image" content="link_to_image"/>
    <meta property="twitter:image:src" content="https://bestsite.com/logo.png">
    <meta property="twitter:image:width" content="1200">
    <meta property="twitter:image:height" content="1200">
    <!-- Google+ -->
    <meta itemprop="name" content="The best site">
    <meta itemprop="description" content="description goes here">
    <meta itemprop="image" content="https://bestsite.com/logo.png">
    <!-- Tags that are not meta -->
    <title>The Best Site</title>
    <!-- CSS -->
    <!-- CSS Reset Files must be called before any other file -->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/reset.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/bundle.css">
    <!-- Analytics scripts can be placed in head, being minified and uglyfied -->
    <script async src="//www.google-analytics.com/analytics.min.js"></script>
  </head>
  <body>

    <!-- Body Content -->
    <!-- Preferably, only a tag containing the root of the application should be called -->
    <app id="app"></app>
    <!-- Another example: -->
    <!-- <div id="app"></div> -->

    <!-- JavaScript -->
    <!-- Place the bundle.js below so that the page loads regardless of JavaScript -->
    <!-- There may be scenarios where bundle.js should be imported into the head tag. -->
    <script async charset="utf-8" src="/js/bundle.js"></script>
  </body>
</html>
```

## Considerations for proposed [`index.html`](#html-guide-example)
Note that `reset.min.css` should only be imported if `bundle.css` does not already have it at the beginning of the file. It is recommended to put the `reset.min.css` attached to the `bundle.css` for performance.
Both CSS imports have an `media` attribute.  The browser will not block page loading with this attribute.

The root file with the application must have only one entry point. The application is expected to load at this point. In the case of `index.html` the entry point is:
```html
  <!-- ... -->

  <app id="app"></app>

  <!-- ... -->
```

You should put the `bundle.js` containing the application at the end of the `<body>` tag. On special occasions, this import may be placed at the end of the `<head>` tag.

The use of the `async` attribute in `<script>` tags is already [supported](https://developer.mozilla.org/en/docs/Web/HTML/Element/script#Browser_compatibility) by most browsers.

---

# <a id="plugins-list"></a>CSS Guide
The projects will have the Google [Material Design](https://material.io/guidelines/) as Guideline. Therefore, it is expected that the spacing, styles and behaviors of the components created have Material Design as the basis.

Projects should follow the following styleguide CSS / Stylus / PostCSS:

#### [CSS / Stylus Styleguide](https://github.com/vilaboim/css)

All CSS should be `bundle.css` and a `bundle.css` should be created containing all the CSS of the application directed to a specific target. Global CSS (such as `reset.min.css` ) should be inserted at the beginning of the bundle, since the entire application will be affected.

---

# <a id="plugins-list"></a>JavaScript Guide
## Code style
For us to have effect on the rules mentioned here, the plugins in the atom must be installed and working. The code writing guide can be read [aqui](https://github.com/airbnb/javascript).

All created code should follow the following `.eslintrc.js` , where it was based on the company's [Airbnb](https://github.com/airbnb/javascript):

```javascript
module.exports = {
  root: true,
  parser: 'babel-eslint',
  parserOptions: {
    sourceType: 'module'
  },
  extends: 'airbnb-base',
  // required to lint *.vue files
  plugins: [
    'html'
  ],
  // check if imports actually resolve
  'settings': {
    'import/resolver': {
      'webpack': {
        'config': 'build/webpack.base.conf.js'
      }
    }
  },
  // add your custom rules here
  'rules': {
    // don't require .vue extension when importing
    'import/extensions': ['error', 'always', {
      'js': 'never',
      'vue': 'never'
    }],
    'one-var': 0,
    'no-param-reassign': 0,
    'import/prefer-default-export': 0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0
  }
}
```

Directories that should not be parsed by ESLint should be added to the `.eslintignore` file, like the one below:
```
build/*.js
config/*.js
```

---

# <a id="nodejs-and-package-manager"></a>Node.js and Package Manager

## <a id="nodejs"></a>Node.js
It is always recommended to use the LTS version recommended by Node.js foundation. Today, 30/12/2016 , the recommended version is 6.9.2.

## <a id="package-manager"></a>Gerenciador de pacotes
Any package manager can be used, however, **[yarn](https://www.npmjs.com/package/yarn)** is recommended as the default where it is expected to be installed globally on the development machine / container.

To install yarn, simply:
<a id="how-install-yarn"></a>
```bash
$ npm install -g yarn
```

#### <a id="yarn-advantages"></a>Some of the advantages of using yarn:
* **Offline Mode**. If you've installed a package before, you can install it again without any internet connection.
* **Deterministic**. The same dependencies will be installed in the same exact way on any machine, regardless of install order.
* **Network Performance**. Yarn efficiently queues up requests and avoids request waterfalls in order to maximize network utilization.
* **Network Resilience**. A single request failing won't cause an install to fail. Requests are retried upon failure.
* **Flat Mode**. Yarn resolves mismatched versions of dependencies to a single version to avoid creating duplicates.

---

# <a id="seed"></a>Seed

## <a id="architecture"></a>Architecture

```bash
├── src
│   ├── App.vue
│   ├── assets
│   │   └── logo.png
│   ├── common
│   │   ├── directives
│   │   │   └── masks.js
│   │   ├── functions
│   │   │   └── helpers.js
│   │   ├── resources
│   │   │   └── resources.js
│   │   └── validations
│   │       ├── email.js
│   │       └── social.js
│   ├── spa
│   │   ├── Hello.vue
│   │   └── Home.vue
│   └── vuex
│   │   ├── actions.js
│   │   ├── getters.js
│   │   ├── modules
│   │   │   └── example.js
│   │   ├── mutation-types.js
│   │   └── store.js
│   ├── main.js
│   └── router-config.js
├── index.html
└── package.json
```

With this architecture, we have:

* **common**. Directory where files that are shared by the entire application;
* **common/directives**. Vue directives that can be used in the application;
* **common/functions**.  Functions used in the application;
* **common/resources**. Resources for consumption of API Rest;
* **common/validations**.  Customized validations that meet the needs of the application;
* **shared-components**.  Where all components that are frequently used in the application should be created and placed here;
* **spa**.  Where each of the SPA pages will be created;
* **vuex**. All files needed for vuex to work. It includes actions, mutations, getters, and store;
* **vuex/modules**. Vuex modules that will be used in the application.
* **`router-config.js`**.  File that configures a VueRouter object to be used in the application. All routes are here. This file is the root file of routes, so external files can be imported and used here;
* **`App.vue`**. Root component of the application already with vue-router started, as well, with added slide-fade transition;
* **`main.js`**. Config file root application. Where everything starts.

## <a id="vuejs"></a>Vue.js
This seed uses the following key technologies:

1. [Vue.js](https://github.com/vuejs/vue/releases/tag/v2.1.8);
* [vuex](https://github.com/vuejs/vuex/releases/tag/v2.1.1);
* [vue-resource](https://github.com/pagekit/vue-resource/releases/tag/1.0.3);
* [vue-router](https://github.com/vuejs/vue-router/releases/tag/v2.1.1);
* [vee-validate](https://github.com/logaretm/vee-validate/releases/tag/2.0.0-beta.18).

## <a id="creating-component"></a>Criação de componentes

A [style guide for vuejs components is used](https://github.com/pablohpsilva/vuejs-component-style-guide/blob/master/README.md).  An example of a component:

```html
<template lang="html">
  <div class="RangeCustom__Wrapper">
    <input type="range" :max="max" :min="min" v-model="value"/>
    <span>Current value is: {{ value }}</span>
  </div>
</template>

<script type="text/babel">
  export default {
    name: 'RangeCustom',
    props: {
      max: {
        type: Number,
        default: 10,
      },
      min: {
        type: Number,
        default: 0,
      },
      defaultValue: {
        type: Number,
        default: 4,
      },
    },
    data() {
      return {
        value: this.defaultValue + 0,
      };
    },
  };
</script>

<style scoped>
  .RangeCustom__Wrapper { /* nice CSS */ }
</style>
```
