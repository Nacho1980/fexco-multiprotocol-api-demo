import http from "k6/http";
import { sleep } from "k6";

export let options = {
  vus: 50,
  duration: "10m",
  thresholds: {
    http_req_duration: ["p(99)<1000"] // 99% of requests must complete below 1s
  }
};

/**
 * Stress tests for the REST API using K6
 * @author Ignacio Santos
 */
export default function() {
  // GET car listing
  http.get("http://192.168.1.37:8080/api/v1/cars");

  // POST for the submission of a new car
  const urlPost = "http://192.168.1.37:8080/api/v1/new";
  const car = {
    brand: "Lamborghini",
    model: "Aventador",
    location: "NY",
    color: "Yellow",
    price: 339000
  };
  const payloadPost = JSON.stringify(car);
  const paramsPost = {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    }
  };
  let res = http.post(urlPost, payloadPost, paramsPost);
  var o = JSON.parse(res.body);

  // DELETE a car (given the id)
  const urlDelete = "http://192.168.1.37:8080/api/v1/cars/" + o.id;
  http.del(urlDelete);
  sleep(1);
}
