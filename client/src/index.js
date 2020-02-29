import "babel-polyfill";
import "bootstrap/dist/css/bootstrap.css";
import App from "./App";
import React from "react";
import ReactDOM from "react-dom";
import "isomorphic-fetch";
const title = "Luxury Car Dealer";
ReactDOM.render(<App />, document.getElementById("container"));
module.hot.accept();
