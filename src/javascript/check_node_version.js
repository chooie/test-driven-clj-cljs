let fs = require("fs");
let packageJson = JSON.parse(fs.readFileSync("package.json"));

let rawNodeVersion = process.version; // vxx.xx.xx
let actualNodeVersion = rawNodeVersion.substring(1);
let requiredNodeVersion = packageJson.engines.node;

if (actualNodeVersion !== requiredNodeVersion) {
  console.log(
    "Must have nodejs version '" + requiredNodeVersion + "'. " +
      "You're using '" + actualNodeVersion + "'."
  );
  process.exit(1);
}

console.log("Node Version Check: OK (" + rawNodeVersion + ")");
