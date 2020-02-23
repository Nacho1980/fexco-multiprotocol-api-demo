import logo from "./luxury-car-silhouette-black.jpg";
import "./App.css";

import React, { Component } from "react";
import Cars from "./components/Cars";
import AmqpMessageToast from "./components/AmqpMessageToast";

class App extends Component {
  state = {
    cars: [
      {
        id: "1",
        brand: "Rolls Royce",
        model: "Phantom",
        color: "White",
        location: "Sevilla",
        price: "520000"
      }
    ],
    inputs: {
      brand: "Ferrari",
      model: "",
      location: "",
      color: "",
      price: 0
    },
    amqpMessage: ""
  };

  componentDidMount() {
    this.getAllCars();
    // this.amqpListener();
  }

  getAllCars() {
    fetch("http://localhost:8080/api/v1/cars", {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      }
    })
      .then(res => res.json())
      .then(data => {
        this.setState({ cars: data });
      })
      .catch(console.log);
  }

  handleChange(e) {
    this.setState({
      inputs: {
        ...this.state.inputs,
        [e.target.name]: e.target.value
      }
    });
  }

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

  handleSubmit(e) {
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
      fetch("http://localhost:8080/api/v1/new", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(car)
      })
        .then(res => res.json())
        .then(this.getAllCars())
        .catch(console.log);
    }
  }

  handleDelete(id) {
    //TODO
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="restrict-size" alt="logo" />
          <h1>LUXURY CARS DEALER INC.</h1>
          <p>Home of the least imaginative dealer name</p>
        </header>
        {this.state.amqpMessage ? (
          <AmqpMessageToast message={this.state.amqpMessage} />
        ) : null}
        <div className="container-fluid App-body pb-5">
          <div className="row">
            <div className="col-4 pb-3">
              <form onSubmit={e => this.handleSubmit.call(this, e)}>
                <div className="form-group">
                  <label htmlFor="brand">Brand</label>
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
                  <label htmlFor="model">Model</label>
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
                  <label htmlFor="color">Color</label>
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
                  <label htmlFor="price">Price</label>
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
                  <label htmlFor="location">Location</label>
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
                <button type="submit" className="btn btn-primary">
                  New
                </button>
              </form>
            </div>
            <div className="col-8">
              <Cars
                cars={this.state.cars}
                onDeleteFunction={this.handleDelete}
              />
            </div>
          </div>
        </div>
        <p className="pt-2">
          Developed by Ignacio Santos. Check it out in{" "}
          <a href="https://github.com/Nacho1980/fexco-multiprotocol-api-demo">
            Github
          </a>
        </p>
      </div>
    );
  }
}

export default App;
