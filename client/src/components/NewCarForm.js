import React, { Component } from "react";
import Alert from "./Alert";

class NewCarForm extends Component {
  state = {
    inputs: {
      brand: "Ferrari",
      model: "",
      location: "",
      color: "",
      price: 0
    },
    error: null
  };

  handleChange(e) {
    this.setState({
      inputs: {
        ...this.state.inputs,
        [e.target.name]: e.target.value
      }
    });
  }

  /**
   * Validate the fields introduced by the user in the new car form
   */
  validateFields() {
    const fields = this.state.inputs;
    return (
      fields.brand !== null &&
      fields.brand !== "" &&
      fields.color !== null &&
      fields.color !== "" &&
      fields.location !== null &&
      fields.location !== "" &&
      fields.model !== null &&
      fields.model !== "" &&
      fields.price !== null &&
      fields.price !== "" &&
      !isNaN(fields.price) &&
      fields.price > 0
    );
  }

  /**
   * Create a new car by submitting the data introduced
   */
  handleSubmit(e) {
    let that = this;
    console.log("submit");
    e.preventDefault();
    let validFields = this.validateFields();
    if (validFields) {
      let car = {
        brand: this.state.inputs.brand,
        model: this.state.inputs.model,
        location: this.state.inputs.location,
        color: this.state.inputs.color,
        price: this.state.inputs.price
      };
      let action = "Added " + car.brand + " " + car.model;
      (async () => {
        const response = await fetch("http://localhost:8080/api/v1/new", {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json"
          },
          body: JSON.stringify(car)
        }).catch(function() {
          that.setState({ error: "Lost connection to backend" });
          console.log("Connection error");
        });
        if (response.status === 200) {
          this.props.updateListFunction();
        }
      })();
    }
  }

  render() {
    return (
      <form onSubmit={e => this.handleSubmit.call(this, e)}>
        {this.state.error && <Alert error={this.state.error} />}
        <div className="form-group">
          <label className="coloredFont" htmlFor="brand">
            Brand
          </label>
          <select
            className="form-control"
            id="brand"
            name="brand"
            value={this.state.inputs.brand}
            onChange={e => this.handleChange.call(this, e)}
          >
            <option value="Ferrari">Ferrari</option>
            <option value="Lamborghini">Lamborghini</option>
            <option value="Rolls Royce">Rolls Royce</option>
            <option value="Bentley">Bentley</option>
            <option value="Porsche">Porsche</option>
            <option value="Bugatti">Bugatti</option>
            <option value="Other">Other</option>
          </select>
        </div>

        <div className="form-group">
          <label className="coloredFont" htmlFor="model">
            Model
          </label>
          <input
            type="text"
            name="model"
            className={
              this.state.inputs.model === ""
                ? "form-control errorInput"
                : "form-control"
            }
            id="model"
            placeholder="Model"
            value={this.state.inputs.model}
            onChange={e => this.handleChange.call(this, e)}
          />
        </div>

        <div className="form-group">
          <label className="coloredFont" htmlFor="color">
            Color
          </label>
          <input
            type="text"
            className={
              this.state.inputs.color === ""
                ? "form-control errorInput"
                : "form-control"
            }
            id="color"
            placeholder="Color"
            name="color"
            value={this.state.inputs.color}
            onChange={e => this.handleChange.call(this, e)}
          />
        </div>

        <div className="form-group">
          <label className="coloredFont" htmlFor="price">
            Price
          </label>
          <input
            type="text"
            className={
              this.state.inputs.price === "" ||
              this.state.inputs.price === 0 ||
              isNaN(this.state.inputs.price)
                ? "form-control errorInput"
                : "form-control"
            }
            id="price"
            placeholder="Price"
            name="price"
            value={this.state.inputs.price}
            onChange={e => this.handleChange.call(this, e)}
          />
        </div>

        <div className="form-group">
          <label className="coloredFont" htmlFor="location">
            Location
          </label>
          <input
            type="text"
            className={
              this.state.inputs.location === ""
                ? "form-control errorInput"
                : "form-control"
            }
            id="location"
            placeholder="Location"
            name="location"
            value={this.state.inputs.location}
            onChange={e => this.handleChange.call(this, e)}
          />
        </div>
        <button type="submit" className="btn btn-primary backgroundButton">
          New
        </button>
      </form>
    );
  }
}

export default NewCarForm;
