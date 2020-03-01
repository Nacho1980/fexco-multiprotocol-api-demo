import logo from "./luxury-car-silhouette-black.jpg";
import "./App.css";
import "regenerator-runtime/runtime";
import React, { Component } from "react";
import CarList from "./components/CarList";
import NewCarForm from "./components/NewCarForm";
import Alert from "./components/Alert";

/**
 * Client application UI developed in React
 */
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
    error: null
  };

  componentDidMount() {
    this.getAllCars();
  }

  /**
   * Get all the cars for the listing via REST service
   */
  getAllCars() {
    console.log("get all cars");
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

  /**
   * Delete the car that the user made click on
   */
  handleDelete(id) {
    let that = this;
    console.log("delete");
    if (id && !isNaN(id) && window.confirm("Delete car?")) {
      const urlCarDelete = "http://localhost:8080/api/v1/cars/" + id;
      let action = "Deleted car " + id;
      (async () => {
        const response = await fetch(urlCarDelete, {
          method: "DELETE"
        }).catch(function() {
          that.setState({ error: "Lost connection to backend" });
          console.log("Connection error");
        });
        if (response.status === 200) {
          this.getAllCars();
        }
      })();
    }
  }

  resetError() {
    this.setState({ error: null });
  }

  /**
   * Render the page in 2 columns: new car form and listing of cars
   */
  render() {
    const that = this;
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="restrict-size" alt="logo" />
          <h1>LUXURY CARS WORLD DEALERS NETWORK INC.</h1>
          <p>Home of the least imaginative company name</p>
        </header>
        <div className="container-fluid App-body pb-5">
          {this.state.error && <Alert error={this.state.error} />}
          <div className="row">
            <div className="col-4 pb-3">
              <NewCarForm
                updateListFunction={e => that.getAllCars.call(that, e)}
              />
            </div>
            <div className="col-8">
              <CarList
                cars={this.state.cars}
                onDeleteFunction={e => that.handleDelete.call(that, e)}
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
