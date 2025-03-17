import { useLocation } from "react-router-dom";
import React, { useEffect, useState } from "react";

import { Box, Button } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import ProductsFormInputs from "../../components/formInputs/ProductsFormInput";
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"

const productsSchema = yup.object().shape({
  name: yup.string().max(70).required(),
  productLine: yup.string().max(50).required(),
  scale: yup.string().max(10).required(),
  vendor: yup.string().max(50).required(),
  description: yup.string().max(4000).required(),
  quantityInStock: yup.number().required(),
  buyPrice: yup.number().required(),
  msrp: yup.number().required()
});

const FormEditProducts = () => {
  const location = useLocation();
  const { rowData } = location.state || {};
  const url = Urls(rowData.id);
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const navigate = useNavigate();

  const [dataProductLine, setDataProductLine] = useState(null);

  useEffect(() => {
    FormListCalls(url.productlines.findByProductLineList, setDataProductLine);
  }, []);

  const initialValues = {
    id: rowData.id, name: rowData.name, productLine: rowData.productLine,
    scale: rowData.scale, vendor: rowData.vendor,
    description: rowData.description, quantityInStock: rowData.quantityInStock,
    buyPrice: rowData.buyPrice, msrp: rowData.msrp
  }

  const handleFormSubmit = async (values, { setSubmitting }) => {
    setStatus('');
    setResponseCode(null);
    try {
      const response = await fetch(url.products.put_Delete, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus('Product updated successfully!');
      } else {
        setStatus(`Error: ${data.title || 'Failed to update Product'} - ${data.detail || ''}`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to update Product'}`);
    }
    setSubmitting(false);
    setDialogOpen(true);
  }

  const handleClose = () => {
    setDialogOpen(false);
    if (responseCode === 200) {
      navigate("/products");
    }
  }

  return (
    <Box m="20px">
      <Header title="EDIT PRODUCT" subtitle="Edit this Product" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={productsSchema}
      >
        {({ values, errors, touched, handleBlur, handleChange, handleSubmit,
          setFieldValue }) => (
          <form onSubmit={handleSubmit}>
            <Box
              display="grid"
              gap="20px"
              gridTemplateColumns="repeat(5, minmax(0, 1fr))"
              sx={{
                "& > div": { gridColumn: isNonMobile ? undefined : "span 5" },
              }}
            >

              <Divider sx={{ gridColumn: "span 5" }} />

              <ProductsFormInputs handleBlur={handleBlur} handleChange={handleChange}
                values={values} touched={touched} errors={errors} isEdit={true}
                setFieldValue={setFieldValue} dataProductLine={dataProductLine} />

            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Save
              </Button>
            </Box>

            <Divider sx={{ gridColumn: "span 5" }} />

          </form>
        )}
      </Formik>

      <OperationStatusDialog
        dialogOpen={dialogOpen} onClose={handleClose} status={status}
        responseCode={responseCode} onClick={handleClose}
      />

    </Box>
  );

}

export default FormEditProducts;
