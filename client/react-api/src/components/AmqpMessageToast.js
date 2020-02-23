import React, { Component } from "react";

class AmqpMessageToast extends Component {
  render() {
    return (
      <div
        role="alert"
        aria-live="assertive"
        aria-atomic="true"
        className="toast"
        data-autohide="false"
      >
        <div className="toast-header">
          <img src="..." className="rounded mr-2" alt="..." />
          <strong className="mr-auto">AQMP message</strong>
          <small>New!</small>
          <button
            type="button"
            class="ml-2 mb-1 close"
            data-dismiss="toast"
            aria-label="Close"
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div className="toast-body">{this.props.message}}</div>
      </div>
    );
  }
}

export default AmqpMessageToast;
