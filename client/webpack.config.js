const webpack = require("webpack");
const path = require("path");
const { CleanWebpackPlugin } = require("clean-webpack-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
  entry: {
    index: "./src/index.js"
  },
  resolve: {
    symlinks: false,
    modules: ["node_modules"],
    alias: {},
    extensions: ["*", ".js", ".jsx"]
  },
  module: {
    rules: [
      {
        loader: "babel-loader",
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        include: [path.resolve(__dirname, "src")]
      },
      {
        test: /\.css$/,
        loader: "style-loader!css-loader"
      },
      {
        test: /\.(jpe?g|png|gif|woff|woff2|eot|ttf|svg)(\?[a-z0-9=.]+)?$/,
        loader: "url-loader?limit=100000"
      }
    ]
  },
  node: {
    net: "empty",
    tls: "empty"
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new CleanWebpackPlugin(),
    new HtmlWebpackPlugin({
      filename: "index.html",
      template: path.resolve(__dirname, "./src/index.html")
    }),
    new HtmlWebpackPlugin({
      filename: "error.html",
      template: path.resolve(__dirname, "./src/index.html")
    })
  ],
  devServer: {
    port: 3000,
    contentBase: "./dist",
    hot: true
  }
};
