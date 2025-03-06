import { useLocation } from "react-router-dom";
import React, { useState, useRef } from "react";

import { Box, Button } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import ProductLinesFormInputEdit from "../../components/formInputs/ProductLinesEdit";
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"

const MAX_FILE_SIZE = 200 * 1024;

const validFileExtension = { image: ['png', 'jpeg'] };

function isValidFileType(fileName, fileType) {
  if (!fileName) return false;
  const extension = fileName.split('.').pop().toLowerCase();
  return validFileExtension[fileType]?.includes(extension);
}

const productLinesSchema = yup.object().shape({
  productLine: yup.string().max(50).required(),
  textDescription: yup.string().max(4000),
  htmlDescription: yup.string(),
  image: yup
    .mixed()
    .nullable()
    .test(
      "is-valid-type",
      "Not a valid image type. Allowed types: png, jpeg",
      (value) => {
        if (!value) return true;
        // Se for uma string, verifique se ela começa com os prefixos conhecidos para jpeg ou png
        if (typeof value === "string") {
          return value.startsWith("/9j/") || value.startsWith("iVBOR");
        }
        // Se for um File, valide pelo nome
        if (value instanceof File) {
          return isValidFileType(value.name, "image");
        }
        return false;
      }
    )
    .test(
      "is-valid-size",
      `File size must be less than ${MAX_FILE_SIZE / 1024}KB`,
      (value) => {
        if (!value) return true;
        if (value instanceof File) {
          return value.size <= MAX_FILE_SIZE;
        }
        return true;
      }
    ),
});

const FormEditProductLines = () => {
  const location = useLocation();
  const { rowData } = location.state || {};
  const url = Urls(rowData.productLine);
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const resetImageRef = useRef(null);
  const navigate = useNavigate();
  const [imageChanged, setImageChanged] = useState(false);

  const initialValues = {
    productLine: rowData.productLine,
    textDescription: rowData.textDescription,
    htmlDescription: rowData.htmlDescription || "",
    image: rowData?.image || "",
  };

  console.log("rowData:", rowData);

  const handleFormSubmit = async (values, { setSubmitting }) => {

    setStatus('');
    setResponseCode(null);

    const formData = new FormData();

    // Object.keys(values).forEach((key) => {
    //   if (key === "image" && values[key] instanceof File && values[key].size > 0) {
    //     formData.append(key, values[key]);
    //   } else if (key !== "image") {
    //     formData.append(key, values[key]);
    //   }
    // });


    // Object.keys(values).forEach((key) => {
    //   if (key === "image" && values[key] instanceof File && values[key].size > 0 && imageChanged) {
    //     formData.append(key, values[key]);
    //   } else if (key !== "image") {
    //     formData.append(key, values[key]);
    //   }
    // });

    Object.keys(values).forEach((key) => {
      if (key === "image") {
        if (imageChanged && values[key] instanceof File && values[key].size > 0) {
          formData.append(key, values[key]); // Apenas adiciona a imagem se foi alterada
        }
      } else {
        formData.append(key, values[key]);
      }
    });

    for (let [key, value] of formData.entries()) {
      console.log(key, value);
    }

    try {
      const response = await fetch(url.productlines.findByProductLine_Put_Delete, {
        method: 'PUT',
        credentials: 'include',
        body: formData,
      });

      let data;
      const contentType = response.headers.get("content-type");

      setResponseCode(response.status);

      if (contentType && contentType.includes("application/json")) {
        data = await response.json();
      } else {
        data = await response.text();
      }

      if (response.ok) {
        setStatus('Product Line updated successfully');
      } else {
        setStatus(`Error: ${data.title || 'Failed to update Product Line'} - ${data.detail || ''}`);
      }

      // console.log("response:", data);
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to update Product Line'}`);
      // console.log(error);
    }
    setSubmitting(false);
    setDialogOpen(true);
  }

  const handleClose = () => {
    setDialogOpen(false);
    if (responseCode === 200) {
      navigate("/productlines");
    }
  }


  return (
    <Box m="20px">
      <Header title="EDIT PRODUCT LINE" subtitle="Edit Product Line" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={productLinesSchema}
      >
        {({ values, errors, touched, handleBlur, handleChange, handleSubmit, setFieldValue }) => {

          return (
            <form onSubmit={handleSubmit}>
              <Box
                display="grid"
                gap="20px"
                gridTemplateColumns="repeat(5, minmax(0, 1fr))"
                sx={{
                  "& > div": { gridColumn: isNonMobile ? undefined : "span 5" },
                }}
              >

                <ProductLinesFormInputEdit
                  handleBlur={handleBlur} handleChange={handleChange} values={values} touched={touched}
                  errors={errors} setFieldValue={setFieldValue} onResetImage={(resetFunc) =>
                    (resetImageRef.current = resetFunc)} setImageChanged={setImageChanged}
                />

              </Box>
              <Box display="flex" justifyContent="end" mt="20px">
                <Button type="submit" color="secondary" variant="contained">
                  Save
                </Button>
              </Box>

              <Divider sx={{ gridColumn: "span 5" }} />

            </form>
          )
        }}
      </Formik>

      <OperationStatusDialog
        dialogOpen={dialogOpen} onClose={handleClose} status={status}
        responseCode={responseCode} onClick={handleClose}
      />

    </Box>
  );

}

export default FormEditProductLines;
