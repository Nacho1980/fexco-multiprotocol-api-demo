import React, { Component } from "react";

class Alert extends Component {
  render() {
    return (
      <div className="alert alert-danger" id="alertError">
        {this.props.error}
      </div>
    );
  }
}
export default Alert;
