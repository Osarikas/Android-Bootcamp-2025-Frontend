package ru.sicampus.bootcamp2025.ui.ogranizations.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.sicampus.bootcamp2025.R
import ru.sicampus.bootcamp2025.databinding.FragmentOrganizationDetailBinding
import ru.sicampus.bootcamp2025.domain.entities.OrganizationEntity

class OrganizationDetailFragment : Fragment(R.layout.fragment_organization_detail) {
    private lateinit var organization: OrganizationEntity
    private lateinit var binding: FragmentOrganizationDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrganizationDetailBinding.bind(view)

        arguments?.getParcelable<OrganizationEntity>("organization")?.let {
            organization = it
            binding.name.text = organization.name
            binding.address.text = organization.address
            binding.count.text = "Волонтеры: ${organization.peopleCount}"
        }
    }

    companion object {
        private const val ARG_ORG_ID = "org_id"

        fun newInstance(organization: OrganizationEntity): OrganizationDetailFragment {
            val fragment = OrganizationDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("organization", organization)
            fragment.arguments = bundle
            return fragment
        }
    }

}