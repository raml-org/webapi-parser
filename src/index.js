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
    console.log(`[${res.level}] ${res.message}`);
  });
}).catch(console.error);
