import { useLocation } from "react-router-dom";
import React, { useState } from "react";

import {
  Box, Button, Dialog, DialogActions, DialogContent, DialogContentText,
  DialogTitle,
  useMediaQuery
} from "@mui/material";

import { Formik } from "formik";
import * as yup from "yup";

// TODO -- criar office Form edit inputs
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";

const phoneRegExp = /^((\+[1-9]{1,4}[ -]?)|(\([0-9]{2,3}\)[ -]?)|([0-9]{2,4})[ -]?)*?[0-9]{3,4}[ -]?[0-9]{3,4}$/;

const officesSchema = yup.object().shape({
  city: yup.string().max(50).required(),
  country: yup.string().max(50).required(),
  state: yup.string().max(50),
  phone: yup
    .string()
    .matches(phoneRegExp, "Phone number is not valid")
    .max(50)
    .required(),
  addressLine1: yup.string().max(50).required(),
  addressLine2: yup.string().max(50),
  postalCode: yup.string().max(15).required(),
  territory: yup.string().max(10).required(),
})

const FormEditOffices = () => {
  const location = useLocation();
  const { rowData } = location.state || {};
  const url = Urls(rowData.id);

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const navigate = useNavigate();

}

export default FormEditOffices;
