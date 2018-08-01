#! node

const amf = require('amf-client-js');

amf.plugins.document.WebApi.register();
amf.plugins.document.Vocabularies.register();
amf.plugins.features.AMFValidation.register();

const ramlParser = amf.AMF.raml10Parser();

if (!process.argv[2]) {
  console.log('[usage] amf-thing <raml-file>');
  return;
}

amf.Core.init().then(async () => {
  const baseUnit = await ramlParser.parseFileAsync(`file://${process.argv[2]}`);
  const validations = await amf.AMF.validate(baseUnit, amf.ProfileNames.RAML, amf.ProfileNames.AMFStyle);
  validations.results.map(res => {
    let location = res.location ? res.location : process.argv[2];
    location = location.replace('file://', '');
    let position = res.position
    position = position.start.line + ':' + position.start.column
    const level = res.level.toUpperCase();
    const message = res.message;
    console.log(`[${location}:${position}] ${level} ${message}`);
  });
}).catch(console.error);
