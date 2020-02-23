import React, { Component } from "react";

class Cars extends Component {
  render() {
    return (
      <div>
        {this.props.cars.map(car => (
          <div
            className="card text-white bg-secondary"
            id={car.id}
            key={car.id}
          >
            <div className="card-body">
              <h5 className="card-title">
                {car.brand} {car.model}
              </h5>
              <h6 className="card-subtitle mb-2 blackText">{car.price} €</h6>
              <p className="card-text">
                {car.color} - {car.location}
              </p>
              <button
                type="submit"
                className="btn btn-primary"
                onClick={this.props.onDeleteFunction(car.id)}
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    );
  }
}

export default Cars;
