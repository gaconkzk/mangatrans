const messages = {
  required: () => 'This is mandatory',
  email: () => 'Email is invalid',
};

const dictionary = {
  en: {
    messages,
  },
  pt: {
    messages,
  },
  ptbr: {
    messages,
  },
};

export default (Validator) => {
  Validator.updateDictionary(dictionary);
};
