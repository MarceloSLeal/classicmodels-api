const PutForms = async (values, url) => {

  const response = await fetch(url, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',

    body: JSON.stringify(values),
  })

  return response;
}

export default PutForms;
