import React, { useState, useEffect } from "react";

import { Box, Button } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import CustomersFormInputs from "../../components/formInputs/Customers";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog";
import PostForms from "../../components/formsRequests/PostForms";

const initialValues = {
  name: "", productLine: "", scale: "", vendor: "", description: "",
  quantityInStock: "", buyPrice: "", MSRP: ""
}

const productsSchema = yup.object().shape({
  name: yup.string().max(70).required(),
  productLine: yup.string().max(50).required(),
  scale: yup.string().max(10).required(),
  vendor: yup.string().max(50).required(),
  description: yup.string().max(4000).required(),
  quantityInStock: yup.number().required(),
  byPrice: yup.number().required(),
  msrp: yup.number().required()
});

const FormAddProducts = () => {
  const url = Urls();

  const [dataProductLine, setDataProductLine] = useState(null);

}

export default FormAddProducts;
